/**
 * 인증 상태 슬라이스
 * 사용자 인증 관련 상태 관리 (향후 확장용)
 */
import { StateCreator } from 'zustand';
import { AppStore } from '../types';

export const authSlice: StateCreator<AppStore> = (set) => ({
  // 초기 상태
  user: null,
  isAuthenticated: false,

  // Actions
  setUser: (user: AppStore["user"]) =>
    set({
      user,
      isAuthenticated: !!user,
    }),

  logout: () =>
    set({
      user: null,
      isAuthenticated: false,
    }),
});

