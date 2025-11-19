/**
 * Store 공통 타입 정의
 */

// 메시지 타입 (기존 page.tsx에서 사용하던 타입)
export interface Message {
  id: number;
  role: "user" | "assistant";
  content: string;
}

// API 상태 타입
export interface ApiState {
  loading: boolean;
  error: string | null;
}

// UI 상태 타입
export interface UiState {
  messages: Message[];
  input: string;
}

// 인증 상태 타입 (향후 확장용)
export interface AuthState {
  user: {
    id: string;
    name: string;
    email: string;
  } | null;
  isAuthenticated: boolean;
}

// 전체 Store 타입
export interface AppStore extends UiState, ApiState, AuthState {
  // UI Actions
  setInput: (input: string) => void;
  addMessage: (message: Message | Omit<Message, 'id'>) => void;
  setMessages: (messages: Message[]) => void;
  clearMessages: () => void;
  
  // API Actions
  setLoading: (loading: boolean) => void;
  setError: (error: string | null) => void;
  
  // Auth Actions (향후 확장용)
  setUser: (user: AuthState["user"]) => void;
  logout: () => void;
}

