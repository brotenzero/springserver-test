/**
 * Store 접근 Custom Hook
 * Zustand store를 직접 사용 (Context 불필요)
 */
import { useAppStore } from './store';
import { AppStore } from './types';

/**
 * Store의 특정 상태만 선택하여 구독
 * @param selector - 선택자 함수
 * @returns 선택된 상태 값
 */
export function useStore<T>(selector: (state: AppStore) => T): T {
  return useAppStore(selector);
}

/**
 * Store 전체 접근 (선택자 없이)
 * @returns store 인스턴스
 */
export function useStoreApi() {
  return useAppStore;
}


