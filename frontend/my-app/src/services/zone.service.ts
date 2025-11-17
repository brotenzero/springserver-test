import { apiClient } from '@/lib/api-client';

export interface Zone {
  id?: string;
  [key: string]: any;
}

export const zoneService = {
  /**
   * 모든 Zone 조회
   */
  getZones: (): Promise<Zone[]> => {
    return apiClient.get<Zone[]>('/zone/api/zones');
  },

  /**
   * 특정 Zone 조회
   */
  getZone: (id: string): Promise<Zone> => {
    return apiClient.get<Zone>(`/zone/api/zones/${id}`);
  },
};

