/**
 * Templates: ConsultLayout
 * 전문가 상담 레이아웃 템플릿
 */
'use client';

import React from 'react';
import { ChatPanel, PreviewPanel } from '../organisms';

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

interface PreviewData {
  title: string;
  content: string;
  highlights?: Array<{
    text: string;
    tooltip?: string;
    variant?: 'default' | 'error';
  }>;
  aiComment?: {
    type: 'info' | 'error';
    content: string;
  };
}

interface ConsultLayoutProps {
  messages: Message[];
  checklist: ChecklistItem[];
  previewSections: PreviewData[];
  onSendMessage: (message: string) => void;
  isLoading?: boolean;
}

export const ConsultLayout: React.FC<ConsultLayoutProps> = ({
  messages,
  checklist,
  previewSections,
  onSendMessage,
  isLoading,
}) => {
  return (
    <div className="flex h-full">
      <ChatPanel
        messages={messages}
        checklist={checklist}
        onSendMessage={onSendMessage}
        isLoading={isLoading}
      />
      <PreviewPanel sections={previewSections} />
    </div>
  );
};

