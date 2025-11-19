/**
 * API 상태 슬라이스
 * API 호출 관련 로딩, 에러 상태 관리
 */
import { StateCreator } from 'zustand';
import { AppStore } from '../types';

export const apiSlice: StateCreator<AppStore> = (set) => ({
  // 초기 상태
  loading: false,
  error: null,

  // Actions
  setLoading: (loading: boolean) => set({ loading }),

  setError: (error: string | null) => set({ error }),
});

