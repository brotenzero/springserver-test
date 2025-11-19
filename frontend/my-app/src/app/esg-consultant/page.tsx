/**
 * Pages: ESG Consultant Page
 * AI ESG Consultant 메인 페이지
 */
'use client';

import React, { useState } from 'react';
import { Header, TabNavigation, ConsultLayout, ReportView } from '@/components';
import type { TabType } from '@/components';

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

export default function ESGConsultantPage() {
  const [activeTab, setActiveTab] = useState<TabType>('consult');
  const [messages, setMessages] = useState<Message[]>([
    {
      id: 1,
      role: 'assistant',
      content: '2단계: 문단 분석 및 부족정보 파악이 완료되었습니다. IFRS S2 기준에 맞춰 몇 가지 질문을 시작하겠습니다.',
      sender: 'AI ESG Consultant',
    },
    {
      id: 2,
      role: 'assistant',
      content: '질문 (S2-5 거버넌스): 경영진의 기후 리스크 감독 주체가 누구인가요? (예: 이사회, ESG 위원회)',
      sender: 'AI ESG Consultant',
    },
    {
      id: 3,
      role: 'user',
      content: '이사회 산하의 \'지속가능경영위원회\'가 분기별로 감독합니다.',
      sender: '사용자',
    },
    {
      id: 4,
      role: 'assistant',
      content: '좋습니다. S2-5 문단이 우측에 생성되었습니다. 확인해주세요.\n\n다음 질문 (S2-15 관련): Scope 1·2 데이터의 기준연도는 어떻게 되나요?',
      sender: 'AI ESG Consultant',
    },
  ]);

  const [checklist] = useState<ChecklistItem[]>([
    { label: 'S2-5: 거버넌스 (감독 주체)', status: 'completed' },
    { label: 'S2-7: 리스크 및 기회 (전략 우선순위)', status: 'warning' },
    { label: 'S2-15: 시나리오 분석 (기준 연도)', status: 'error' },
    { label: 'Scope 1, 2, 3 배출량', status: 'error' },
  ]);

  const [previewSections] = useState<PreviewData[]>([
    {
      title: 'IFRS S2-5: Governance',
      content: '당사는 기후 관련 리스크 및 기회에 대한 효과적인 감독을 위해 이사회 산하 \'지속가능경영위원회\'를 설치하여 운영하고 있습니다. 위원회는 분기별로 기후 관련 주요 안건을 보고받고, 관련 전략 및 성과를 감독합니다.',
      highlights: [
        {
          text: '이사회 산하 \'지속가능경영위원회\'',
          tooltip: '근거: 사용자 입력 (Q:감독 주체)',
          variant: 'default',
        },
        {
          text: '분기별',
          tooltip: '근거: TCFD 보고서 p.5 (수정됨)',
          variant: 'default',
        },
      ],
      aiComment: {
        type: 'info',
        content: '기준서 충족. \'위원회\'의 구체적인 역할(예: 성과 측정, 보상 연계)을 추가하면 더 좋습니다.',
      },
    },
    {
      title: 'IFRS S2-15: Scenario Analysis',
      content: '당사는 NZE 2050, 2도 시나리오 등을 활용하여 기후 관련 전환 리스크를 분석합니다. [기준연도 데이터 입력 필요]를 기준으로 분석을 수행하였으며...',
      highlights: [
        {
          text: '[기준연도 데이터 입력 필요]',
          tooltip: '근거: 정량요소 부족 (AI가 수정 요청)',
          variant: 'error',
        },
      ],
      aiComment: {
        type: 'error',
        content: '정량요소 부족. \'Scope 1·2 데이터의 기준연도\'를 왼쪽 채팅창에 입력해주세요.',
      },
    },
  ]);

  const handleSendMessage = (message: string) => {
    const userMessage: Message = {
      id: messages.length + 1,
      role: 'user',
      content: message,
      sender: '사용자',
    };
    setMessages((prev) => [...prev, userMessage]);

    // AI 응답 시뮬레이션
    setTimeout(() => {
      const aiMessage: Message = {
        id: messages.length + 2,
        role: 'assistant',
        content: '답변을 확인했습니다. 추가 정보가 필요하면 알려주세요.',
        sender: 'AI ESG Consultant',
      };
      setMessages((prev) => [...prev, aiMessage]);
    }, 1000);
  };

  const handleExportPDF = () => {
    console.log('PDF 내보내기');
    // PDF 내보내기 로직
  };

  const handleExportWord = () => {
    console.log('Word 내보내기');
    // Word 내보내기 로직
  };

  return (
    <div className="h-screen flex flex-col bg-gray-50">
      <Header
        showExportButtons={activeTab === 'report'}
        reportVersion="v1.0 (초안)"
        onExportPDF={handleExportPDF}
        onExportWord={handleExportWord}
      />

      <main className="flex-1 flex flex-col overflow-hidden">
        <TabNavigation activeTab={activeTab} onTabChange={setActiveTab} />

        <div className="flex-1 overflow-hidden">
          {activeTab === 'consult' ? (
            <ConsultLayout
              messages={messages}
              checklist={checklist}
              previewSections={previewSections}
              onSendMessage={handleSendMessage}
              isLoading={false}
            />
          ) : (
            <ReportView
              title="연간 기후 공시 보고서 (IFRS S2 기반)"
              version="v1.0"
              sections={[
                {
                  title: '1. Governance (S2-5)',
                  content: (
                    <>
                      <p>
                        당사는 기후 관련 리스크 및 기회에 대한 효과적인 감독을 위해 이사회 산하
                        '지속가능경영위원회'를 설치하여 운영하고 있습니다. 위원회는 분기별로 기후
                        관련 주요 안건을 보고받고, 관련 전략 및 성과를 감독합니다.
                      </p>
                      <p>
                        경영진은 위원회에서 승인된 기후 전략을 이행하며, 기후 리스크 식별 및 평가에
                        대한 책임을 집니다.
                      </p>
                    </>
                  ),
                },
                {
                  title: '2. Risks & Opportunities (S2-7)',
                  content: (
                    <>
                      <p>
                        당사는 단기, 중기, 장기에 걸쳐 식별된 주요 기후 관련 리스크와 기회를
                        관리합니다. 주요 전환 리스크로는 탄소 배출 규제 강화가 있으며, 물리적 리스크로는
                        극한 기후 현상 증가를 식별하였습니다.
                      </p>
                      <p>
                        기회 요인으로는 저탄소 제품 및 서비스 시장 확대를 식별하고, 관련 기술 개발에
                        R&D 투자를 집중하고 있습니다.
                      </p>
                    </>
                  ),
                },
                {
                  title: '3. Metrics & Targets (S2-15)',
                  content: (
                    <>
                      <p>
                        당사의 온실가스 배출량은 2023년(기준연도) 대비 2030년까지 Scope 1, 2 배출량을
                        40% 감축하는 것을 목표로 합니다.
                      </p>
                      <p className="font-semibold text-gray-800">주요 지표:</p>
                      <ul className="list-disc pl-5">
                        <li>Scope 1 배출량 (tCO2e)</li>
                        <li>Scope 2 배출량 (Market-based, tCO2e)</li>
                        <li>기후 관련 전환 리스크에 노출된 자산 (금액)</li>
                      </ul>
                    </>
                  ),
                },
              ]}
            />
          )}
        </div>
      </main>
    </div>
  );
}

