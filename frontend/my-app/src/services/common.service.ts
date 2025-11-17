import { apiClient } from '@/lib/api-client';

export interface CommonData {
  id?: string;
  [key: string]: any;
}

export const commonService = {
  /**
   * 공통 데이터 조회
   */
  getData: (): Promise<CommonData[]> => {
    return apiClient.get<CommonData[]>('/common/api/data');
  },

  /**
   * 특정 데이터 조회
   */
  getDataById: (id: string): Promise<CommonData> => {
    return apiClient.get<CommonData>(`/common/api/data/${id}`);
  },
};

