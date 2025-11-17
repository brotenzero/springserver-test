import { apiClient } from '@/lib/api-client';

export interface EurekaApplication {
  name: string;
  instance: Array<{
    instanceId: string;
    hostName: string;
    app: string;
    ipAddr: string;
    status: string;
    port: {
      $: number;
      '@enabled': string;
    };
  }>;
}

export interface EurekaApplications {
  applications: {
    application: EurekaApplication[];
  };
}

export const eurekaService = {
  /**
   * Eureka Server에서 등록된 모든 애플리케이션 조회
   * 주의: Eureka는 직접 접근이므로 포트 8761 사용
   */
  getApplications: async (): Promise<EurekaApplications> => {
    // Eureka는 API Gateway를 거치지 않고 직접 접근
    const eurekaUrl = typeof window === 'undefined'
      ? 'http://eurekaserver:8761'
      : 'http://localhost:8761';
    
    return apiClient.get<EurekaApplications>(`${eurekaUrl}/eureka/apps`);
  },

  /**
   * 특정 애플리케이션의 인스턴스 조회
   */
  getApplication: async (appName: string): Promise<EurekaApplication> => {
    const eurekaUrl = typeof window === 'undefined'
      ? 'http://eurekaserver:8761'
      : 'http://localhost:8761';
    
    return apiClient.get<EurekaApplication>(`${eurekaUrl}/eureka/apps/${appName.toUpperCase()}`);
  },
};

