/**
 * Organisms: ChatPanel
 * 채팅 패널 컴포넌트
 */
'use client';

import React, { useState, useRef, useEffect } from 'react';
import { ChatBubble, ChecklistItem } from '../molecules';
import { Textarea, Button, Icon } from '../atoms';

interface Message {
  id: number;
  role: 'user' | 'assistant';
  content: string;
  sender?: string;
}

interface ChecklistItem {
  label: string;
  status: 'completed' | 'warning' | 'error';
}

interface ChatPanelProps {
  messages: Message[];
  checklist: ChecklistItem[];
  onSendMessage: (message: string) => void;
  isLoading?: boolean;
}

export const ChatPanel: React.FC<ChatPanelProps> = ({
  messages,
  checklist,
  onSendMessage,
  isLoading = false,
}) => {
  const [input, setInput] = useState('');
  const chatEndRef = useRef<HTMLDivElement>(null);
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    chatEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  useEffect(() => {
    if (textareaRef.current) {
      textareaRef.current.style.height = 'auto';
      textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
    }
  }, [input]);

  const handleSend = () => {
    if (!input.trim() || isLoading) return;
    onSendMessage(input);
    setInput('');
  };

  const handleKeyPress = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSend();
    }
  };

  return (
    <div className="w-1/2 flex flex-col h-full bg-white border-r border-gray-200">
      {/* 체크리스트 */}
      <div className="p-4 border-b border-gray-200">
        <h2 className="text-lg font-semibold text-gray-900 mb-3">
          IFRS S2 필수 정보 체크리스트
        </h2>
        <ul className="space-y-2">
          {checklist.map((item, index) => (
            <ChecklistItem
              key={index}
              label={item.label}
              status={item.status}
            />
          ))}
        </ul>
      </div>

      {/* 채팅 히스토리 */}
      <div className="flex-1 flex flex-col overflow-hidden">
        <div className="flex-1 overflow-y-auto p-6 space-y-4">
          {messages.map((message) => (
            <ChatBubble
              key={message.id}
              role={message.role}
              content={message.content}
              sender={message.sender}
            />
          ))}
          <div ref={chatEndRef} />
        </div>

        {/* 입력창 */}
        <div className="p-4 bg-gray-50 border-t border-gray-200">
          <div className="flex items-center space-x-3">
            <textarea
              ref={textareaRef}
              value={input}
              onChange={(e) => setInput(e.target.value)}
              onKeyPress={handleKeyPress}
              placeholder="AI의 질문에 답변하거나, 궁금한 점을 입력하세요... (예: 이 문장은 S2-7에 맞나요?)"
              rows={1}
              disabled={isLoading}
              className="flex-1 p-3 border border-gray-300 rounded-lg resize-none focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <Button
              onClick={handleSend}
              disabled={!input.trim() || isLoading}
              className="p-3"
            >
              <Icon name="Send" size={20} />
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

