import { apiClient } from '@/lib/api-client';

export interface SoccerMatch {
  id?: string;
  [key: string]: any;
}

export interface SearchParams {
  keyword: string;
  type?: string;
  [key: string]: any;
}

export const soccerService = {
  /**
   * 모든 축구 경기 조회
   */
  getMatches: (): Promise<SoccerMatch[]> => {
    return apiClient.get<SoccerMatch[]>('/soccer/api/matches');
  },

  /**
   * 특정 경기 조회
   */
  getMatch: (id: string): Promise<SoccerMatch> => {
    return apiClient.get<SoccerMatch>(`/soccer/api/matches/${id}`);
  },

  /**
   * 키워드로 검색
   */
  searchByKeyword: (params: SearchParams): Promise<SoccerMatch[]> => {
    return apiClient.getWithQuery<SoccerMatch[]>('/soccer/search/findByKeyword', params);
  },

  /**
   * 경기 생성
   */
  createMatch: (data: Partial<SoccerMatch>): Promise<SoccerMatch> => {
    return apiClient.post<SoccerMatch>('/soccer/api/matches', data);
  },

  /**
   * 경기 업데이트
   */
  updateMatch: (id: string, data: Partial<SoccerMatch>): Promise<SoccerMatch> => {
    return apiClient.put<SoccerMatch>(`/soccer/api/matches/${id}`, data);
  },

  /**
   * 경기 삭제
   */
  deleteMatch: (id: string): Promise<void> => {
    return apiClient.delete<void>(`/soccer/api/matches/${id}`);
  },
};

