/**
 * UI 상태 슬라이스
 * 메시지, 입력값 등 UI 관련 상태 관리
 */
import { StateCreator } from 'zustand';
import { AppStore, Message } from '../types';

export const uiSlice: StateCreator<AppStore, [], [], Pick<AppStore, 'messages' | 'input' | 'setInput' | 'addMessage' | 'setMessages' | 'clearMessages'>> = (set) => ({
  // 초기 상태
  messages: [
    { id: 1, role: "assistant", content: "안녕하세요! 무엇을 도와드릴까요?" },
  ],
  input: "",

  // Actions
  setInput: (input: string) => set({ input }),

  addMessage: (message: Omit<Message, 'id'> | Message) =>
    set((state) => {
      // ID가 이미 있으면 그대로 사용, 없으면 자동 생성
      const newMessage: Message =
        'id' in message && typeof message.id === 'number'
          ? message as Message
          : {
            id: state.messages.length > 0
              ? Math.max(...state.messages.map(m => m.id)) + 1
              : 1,
            ...message,
          };

      return {
        messages: [...state.messages, newMessage],
      };
    }),

  setMessages: (messages: Message[]) => set({ messages }),

  clearMessages: () =>
    set({
      messages: [
        { id: 1, role: "assistant", content: "안녕하세요! 무엇을 도와드릴까요?" },
      ],
    }),
});

