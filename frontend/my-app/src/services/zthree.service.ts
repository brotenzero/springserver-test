import { apiClient } from '@/lib/api-client';

export interface ZThree {
  id?: string;
  [key: string]: any;
}

export const zthreeService = {
  /**
   * 모든 ZThree 데이터 조회
   */
  getZThrees: (): Promise<ZThree[]> => {
    return apiClient.get<ZThree[]>('/zthree/api/zthrees');
  },

  /**
   * 특정 ZThree 조회
   */
  getZThree: (id: string): Promise<ZThree> => {
    return apiClient.get<ZThree>(`/zthree/api/zthrees/${id}`);
  },
};

