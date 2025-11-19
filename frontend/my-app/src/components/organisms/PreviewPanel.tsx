/**
 * Organisms: PreviewPanel
 * 프리뷰 패널 컴포넌트
 */
'use client';

import React from 'react';
import { PreviewSection } from '../molecules';

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

interface PreviewPanelProps {
  sections: PreviewData[];
}

export const PreviewPanel: React.FC<PreviewPanelProps> = ({ sections }) => {
  return (
    <div className="w-1/2 flex flex-col h-full bg-gray-50 overflow-y-auto">
      <div className="p-6">
        <h2 className="text-xl font-semibold text-gray-900 mb-4">
          실시간 보고서 문단 프리뷰
        </h2>
        {sections.map((section, index) => (
          <PreviewSection
            key={index}
            title={section.title}
            content={section.content}
            highlights={section.highlights}
            aiComment={section.aiComment}
          />
        ))}
      </div>
    </div>
  );
};

