# Zustand 단일 Store 전략

## 📋 개요

프로젝트 전체에서 **단일 Zustand Store**를 사용하고, React Context API로 래핑하여 관리하는 전략입니다.

## 🎯 목표

1. **단일 Store 원칙**: 프로젝트 전체에서 하나의 Zustand store만 사용
2. **Context 통합**: React Context API로 래핑하여 타입 안정성 및 접근성 향상
3. **타입 안정성**: TypeScript로 완전한 타입 지원
4. **확장성**: 모듈화된 슬라이스 패턴으로 관리

## 📁 디렉토리 구조

```
src/
├── store/
│   ├── index.ts              # 단일 store export
│   ├── store.ts              # Zustand store 정의
│   ├── slices/               # 기능별 슬라이스
│   │   ├── auth.slice.ts
│   │   ├── ui.slice.ts
│   │   ├── api.slice.ts
│   │   └── index.ts
│   └── types.ts              # 공통 타입 정의
├── context/
│   └── StoreContext.tsx      # Context Provider
└── hooks/
    └── useStore.ts           # Store 접근 훅
```

## 🔧 구현 단계

### 1단계: 의존성 설치

```bash
cd frontend/my-app
pnpm add zustand
```

### 2단계: Store 타입 정의

**`src/store/types.ts`**
- 공통 인터페이스 및 타입 정의
- Store 상태 타입 정의

### 3단계: 슬라이스(Slice) 생성

**패턴**: 각 기능별로 슬라이스를 분리하되, 하나의 store에 통합

- `auth.slice.ts`: 인증 관련 상태
- `ui.slice.ts`: UI 상태 (모달, 테마 등)
- `api.slice.ts`: API 호출 상태 (로딩, 에러 등)

### 4단계: 단일 Store 생성

**`src/store/store.ts`**
- 모든 슬라이스를 하나의 store로 통합
- `create()` 함수로 단일 store 인스턴스 생성

### 5단계: Context Provider 생성

**`src/context/StoreContext.tsx`**
- Zustand store를 Context로 래핑
- Provider 컴포넌트 생성
- 타입 안정성 보장

### 6단계: Custom Hook 생성

**`src/hooks/useStore.ts`**
- Context를 통한 store 접근 훅
- 타입 안전한 선택자(selector) 지원

### 7단계: RootLayout에 Provider 추가

**`src/app/layout.tsx`**
- StoreContext.Provider로 앱 전체 래핑

## 📝 구현 상세

### Store 구조 예시

```typescript
// src/store/store.ts
import { create } from 'zustand';
import { devtools, persist } from 'zustand/middleware';
import { authSlice } from './slices/auth.slice';
import { uiSlice } from './slices/ui.slice';
import { apiSlice } from './slices/api.slice';

export const useAppStore = create<AppStore>()(
  devtools(
    persist(
      (...a) => ({
        ...authSlice(...a),
        ...uiSlice(...a),
        ...apiSlice(...a),
      }),
      { name: 'app-store' }
    )
  )
);
```

### Context 래핑

```typescript
// src/context/StoreContext.tsx
'use client';
import { createContext, useContext } from 'react';
import { useAppStore } from '@/store/store';

const StoreContext = createContext<typeof useAppStore | null>(null);

export function StoreProvider({ children }: { children: React.ReactNode }) {
  return (
    <StoreContext.Provider value={useAppStore}>
      {children}
    </StoreContext.Provider>
  );
}

export function useStoreContext() {
  const context = useContext(StoreContext);
  if (!context) {
    throw new Error('useStoreContext must be used within StoreProvider');
  }
  return context;
}
```

### Custom Hook

```typescript
// src/hooks/useStore.ts
import { useStoreContext } from '@/context/StoreContext';
import { AppStore } from '@/store/types';

export function useStore<T>(selector: (state: AppStore) => T): T {
  const store = useStoreContext();
  return store(selector);
}
```

## 🎨 사용 예시

### 컴포넌트에서 사용

```typescript
'use client';
import { useStore } from '@/hooks/useStore';

export default function MyComponent() {
  // 특정 상태만 선택
  const user = useStore((state) => state.user);
  const isLoading = useStore((state) => state.api.loading);
  
  // 액션 호출
  const login = useStore((state) => state.auth.login);
  const setLoading = useStore((state) => state.api.setLoading);
  
  return (
    <div>
      {isLoading ? 'Loading...' : user?.name}
    </div>
  );
}
```

## ✅ 장점

1. **단일 진실 공급원(Single Source of Truth)**: 하나의 store로 모든 상태 관리
2. **타입 안정성**: TypeScript로 완전한 타입 체크
3. **성능 최적화**: 필요한 상태만 선택하여 리렌더링 최소화
4. **확장성**: 슬라이스 패턴으로 기능 추가 용이
5. **디버깅**: Zustand DevTools 지원
6. **지속성**: persist 미들웨어로 상태 유지

## 🚀 마이그레이션 가이드

### 기존 useState → Zustand Store

1. **상태 식별**: `page.tsx`의 `useState` 상태들을 식별
2. **슬라이스 생성**: 해당 상태를 적절한 슬라이스로 이동
3. **컴포넌트 수정**: `useState` → `useStore`로 변경
4. **테스트**: 기능 정상 동작 확인

### 예시: Message 상태 마이그레이션

**Before (useState)**
```typescript
const [messages, setMessages] = useState<Message[]>([]);
```

**After (Zustand)**
```typescript
const messages = useStore((state) => state.ui.messages);
const addMessage = useStore((state) => state.ui.addMessage);
```

## 📌 규칙 및 제약사항

1. **단일 Store 원칙**: 프로젝트 전체에서 `useAppStore`만 사용
2. **슬라이스 분리**: 기능별로 슬라이스 분리, store에 통합
3. **Context 필수**: 모든 컴포넌트는 Context를 통해 store 접근
4. **타입 정의**: 모든 상태와 액션은 타입 정의 필수
5. **선택자 사용**: 전체 store를 구독하지 말고 필요한 부분만 선택

## 🔍 다음 단계

1. ✅ Zustand 설치
2. ✅ 디렉토리 구조 생성
3. ✅ 기본 슬라이스 생성 (ui, api)
4. ✅ Context Provider 설정
5. ✅ 기존 컴포넌트 마이그레이션
6. ✅ 테스트 및 검증

