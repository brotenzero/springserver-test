/**
 * 단일 Zustand Store
 * 모든 슬라이스를 하나의 store로 통합
 */
import { create } from 'zustand';
import { devtools, persist } from 'zustand/middleware';
import { AppStore } from './types';
import { uiSlice, apiSlice, authSlice } from './slices';

export const useAppStore = create<AppStore>()(
  devtools(
    persist(
      (...a) => ({
        ...uiSlice(...a),
        ...apiSlice(...a),
        ...authSlice(...a),
      }),
      {
        name: 'app-store',
        // messages는 persist에서 제외 (세션마다 초기화)
        partialize: (state) => ({
          user: state.user,
          isAuthenticated: state.isAuthenticated,
          // messages와 input은 제외하여 매번 초기화
        }),
      }
    ),
    { name: 'AppStore' }
  )
);

