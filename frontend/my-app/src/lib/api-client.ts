/**
 * API Client for communicating with Spring Boot microservices via API Gateway
 * Supports both client-side and server-side rendering
 */

class ApiClient {
  private baseURL: string;

  constructor() {
    // 클라이언트 사이드에서는 브라우저 URL 사용
    // 서버 사이드에서는 컨테이너 이름 사용
    if (typeof window === 'undefined') {
      // Server-side: Docker 내부 네트워크 사용
      this.baseURL = process.env.API_GATEWAY || 'http://discoveryclient:8080';
    } else {
      // Client-side: 브라우저에서 접근 가능한 URL 사용
      this.baseURL = process.env.NEXT_PUBLIC_API_GATEWAY || 'http://localhost:8080';
    }
  }

  /**
   * GET 요청
   */
  async get<T>(path: string, options?: RequestInit): Promise<T> {
    const url = path.startsWith('http') ? path : `${this.baseURL}${path}`;
    
    try {
      const response = await fetch(url, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          ...options?.headers,
        },
        ...options,
      });

      if (!response.ok) {
        throw new Error(`API Error: ${response.status} ${response.statusText}`);
      }

      // 빈 응답 처리
      const contentType = response.headers.get('content-type');
      if (!contentType || !contentType.includes('application/json')) {
        return {} as T;
      }

      return response.json();
    } catch (error) {
      console.error('API GET Error:', error);
      throw error;
    }
  }

  /**
   * POST 요청
   */
  async post<T>(path: string, data?: any, options?: RequestInit): Promise<T> {
    const url = path.startsWith('http') ? path : `${this.baseURL}${path}`;
    
    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          ...options?.headers,
        },
        body: data ? JSON.stringify(data) : undefined,
        ...options,
      });

      if (!response.ok) {
        throw new Error(`API Error: ${response.status} ${response.statusText}`);
      }

      const contentType = response.headers.get('content-type');
      if (!contentType || !contentType.includes('application/json')) {
        return {} as T;
      }

      return response.json();
    } catch (error) {
      console.error('API POST Error:', error);
      throw error;
    }
  }

  /**
   * PUT 요청
   */
  async put<T>(path: string, data?: any, options?: RequestInit): Promise<T> {
    const url = path.startsWith('http') ? path : `${this.baseURL}${path}`;
    
    try {
      const response = await fetch(url, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          ...options?.headers,
        },
        body: data ? JSON.stringify(data) : undefined,
        ...options,
      });

      if (!response.ok) {
        throw new Error(`API Error: ${response.status} ${response.statusText}`);
      }

      const contentType = response.headers.get('content-type');
      if (!contentType || !contentType.includes('application/json')) {
        return {} as T;
      }

      return response.json();
    } catch (error) {
      console.error('API PUT Error:', error);
      throw error;
    }
  }

  /**
   * DELETE 요청
   */
  async delete<T>(path: string, options?: RequestInit): Promise<T> {
    const url = path.startsWith('http') ? path : `${this.baseURL}${path}`;
    
    try {
      const response = await fetch(url, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          ...options?.headers,
        },
        ...options,
      });

      if (!response.ok) {
        throw new Error(`API Error: ${response.status} ${response.statusText}`);
      }

      const contentType = response.headers.get('content-type');
      if (!contentType || !contentType.includes('application/json')) {
        return {} as T;
      }

      return response.json();
    } catch (error) {
      console.error('API DELETE Error:', error);
      throw error;
    }
  }

  /**
   * Query Parameter를 포함한 GET 요청
   */
  async getWithQuery<T>(path: string, params?: Record<string, string | number | boolean>, options?: RequestInit): Promise<T> {
    let url = path.startsWith('http') ? path : `${this.baseURL}${path}`;
    
    if (params) {
      const searchParams = new URLSearchParams();
      Object.entries(params).forEach(([key, value]) => {
        searchParams.append(key, String(value));
      });
      url += `?${searchParams.toString()}`;
    }

    return this.get<T>(url, options);
  }
}

// 싱글톤 인스턴스 export
export const apiClient = new ApiClient();

