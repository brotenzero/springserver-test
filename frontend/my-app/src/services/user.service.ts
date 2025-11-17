import { apiClient } from '@/lib/api-client';

export interface User {
  id?: string;
  name?: string;
  email?: string;
  [key: string]: any;
}

export const userService = {
  /**
   * 모든 사용자 조회
   */
  getUsers: (): Promise<User[]> => {
    return apiClient.get<User[]>('/user/api/users');
  },

  /**
   * 특정 사용자 조회
   */
  getUser: (id: string): Promise<User> => {
    return apiClient.get<User>(`/user/api/users/${id}`);
  },

  /**
   * 사용자 생성
   */
  createUser: (data: Partial<User>): Promise<User> => {
    return apiClient.post<User>('/user/api/users', data);
  },

  /**
   * 사용자 업데이트
   */
  updateUser: (id: string, data: Partial<User>): Promise<User> => {
    return apiClient.put<User>(`/user/api/users/${id}`, data);
  },

  /**
   * 사용자 삭제
   */
  deleteUser: (id: string): Promise<void> => {
    return apiClient.delete<void>(`/user/api/users/${id}`);
  },
};

