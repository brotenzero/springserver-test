"use client";

import { useEffect, useState } from "react";
import { eurekaService } from "@/services/eureka.service";

interface ServiceStatus {
  name: string;
  instances: number;
  status: string;
}

export default function ServicesPage() {
  const [services, setServices] = useState<ServiceStatus[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadServices();
    // 5초마다 자동 새로고침
    const interval = setInterval(loadServices, 5000);
    return () => clearInterval(interval);
  }, []);

  const loadServices = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await eurekaService.getApplications();
      
      const serviceList: ServiceStatus[] = data.applications.application.map((app) => ({
        name: app.name,
        instances: app.instance.length,
        status: app.instance[0]?.status || "UNKNOWN",
      }));

      setServices(serviceList);
    } catch (err) {
      console.error("서비스 목록 로드 실패:", err);
      setError("서비스 목록을 불러올 수 없습니다. Eureka Server가 실행 중인지 확인하세요.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-900 text-white p-8">
      <div className="max-w-6xl mx-auto">
        <div className="flex items-center justify-between mb-8">
          <h1 className="text-3xl font-bold">마이크로서비스 상태</h1>
          <button
            onClick={loadServices}
            disabled={loading}
            className="px-4 py-2 bg-blue-600 hover:bg-blue-700 rounded-lg disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
          >
            {loading ? "로딩 중..." : "새로고침"}
          </button>
        </div>

        {error && (
          <div className="mb-6 p-4 bg-red-900/50 border border-red-700 rounded-lg">
            <p className="text-red-200">{error}</p>
          </div>
        )}

        {loading && !error && (
          <div className="text-center py-12">
            <div className="inline-block animate-spin rounded-full h-12 w-12 border-b-2 border-white"></div>
            <p className="mt-4 text-gray-400">서비스 목록을 불러오는 중...</p>
          </div>
        )}

        {!loading && !error && (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {services.map((service) => (
              <div
                key={service.name}
                className="bg-gray-800 border border-gray-700 rounded-lg p-6 hover:border-gray-600 transition-colors"
              >
                <div className="flex items-center justify-between mb-4">
                  <h2 className="text-xl font-semibold">{service.name}</h2>
                  <span
                    className={`px-3 py-1 rounded-full text-xs font-medium ${
                      service.status === "UP"
                        ? "bg-green-900/50 text-green-300"
                        : "bg-red-900/50 text-red-300"
                    }`}
                  >
                    {service.status}
                  </span>
                </div>
                <div className="text-sm text-gray-400">
                  <p>인스턴스 수: {service.instances}</p>
                </div>
              </div>
            ))}
          </div>
        )}

        {!loading && !error && services.length === 0 && (
          <div className="text-center py-12 text-gray-400">
            등록된 서비스가 없습니다.
          </div>
        )}
      </div>
    </div>
  );
}

