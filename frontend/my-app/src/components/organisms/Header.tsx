/**
 * Organisms: Header
 * 헤더 컴포넌트
 */
'use client';

import React from 'react';
import { Icon, Badge, Button } from '../atoms';

interface HeaderProps {
  showExportButtons?: boolean;
  reportVersion?: string;
  onExportPDF?: () => void;
  onExportWord?: () => void;
}

export const Header: React.FC<HeaderProps> = ({
  showExportButtons = false,
  reportVersion = 'v1.0 (초안)',
  onExportPDF,
  onExportWord,
}) => {
  return (
    <header className="bg-white border-b border-gray-200 shadow-sm w-full z-10">
      <div className="max-w-screen-2xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* 로고 및 타이틀 */}
          <div className="flex items-center space-x-2">
            <div className="bg-blue-600 p-2 rounded-lg">
              <Icon name="MessageSquare" size={20} className="text-white" />
            </div>
            <h1 className="text-xl font-bold text-gray-800">AI ESG Consultant</h1>
            <Badge>Prototype</Badge>
          </div>

          {/* 버전 및 내보내기 버튼 */}
          <div className="flex items-center space-x-4">
            {reportVersion && (
              <span className="text-sm font-medium text-gray-500">
                보고서 버전: {reportVersion}
              </span>
            )}
            {showExportButtons && (
              <>
                <Button
                  variant="danger"
                  size="sm"
                  onClick={onExportPDF}
                  className="flex items-center space-x-1.5"
                >
                  <Icon name="Download" size={18} />
                  <span>PDF 내보내기</span>
                </Button>
                <Button
                  variant="primary"
                  size="sm"
                  onClick={onExportWord}
                  className="flex items-center space-x-1.5"
                >
                  <Icon name="Download" size={18} />
                  <span>Word 내보내기</span>
                </Button>
              </>
            )}
          </div>
        </div>
      </div>
    </header>
  );
};

