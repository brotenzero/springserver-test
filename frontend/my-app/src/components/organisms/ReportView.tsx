/**
 * Organisms: ReportView
 * 최종 보고서 뷰 컴포넌트
 */
'use client';

import React from 'react';

interface ReportSection {
  title: string;
  content: React.ReactNode;
}

interface ReportViewProps {
  title: string;
  version: string;
  sections: ReportSection[];
}

export const ReportView: React.FC<ReportViewProps> = ({
  title,
  version,
  sections,
}) => {
  return (
    <div className="h-full overflow-y-auto bg-white">
      <div className="max-w-4xl mx-auto p-8 lg:p-12">
        <h1 className="text-3xl font-bold text-gray-900 mb-4">{title}</h1>
        <p className="text-gray-600 mb-8">
          본 보고서는 AI ESG Consultant와의 대화형 정보 수집 및 검증을 통해 생성되었습니다. (버전: {version})
        </p>
        
        {sections.map((section, index) => (
          <section key={index} className="mb-10">
            <h2 className="text-2xl font-semibold text-gray-800 border-b-2 border-blue-500 pb-2 mb-4">
              {section.title}
            </h2>
            <div className="prose prose-lg max-w-none text-gray-700 space-y-4">
              {section.content}
            </div>
          </section>
        ))}
      </div>
    </div>
  );
};

