/**
 * Organisms: TabNavigation
 * 탭 네비게이션 컴포넌트
 */
'use client';

import React from 'react';
import { TabButton } from '../molecules';

export type TabType = 'consult' | 'report';

interface TabNavigationProps {
    activeTab: TabType;
    onTabChange: (tab: TabType) => void;
}

export const TabNavigation: React.FC<TabNavigationProps> = ({
    activeTab,
    onTabChange,
}) => {
    return (
        <div className="bg-white border-b border-gray-200">
            <nav className="-mb-px flex max-w-screen-2xl mx-auto px-4 sm:px-6 lg:px-8">
                <TabButton
                    label="전문가 상담 및 문단 생성"
                    isActive={activeTab === 'consult'}
                    onClick={() => onTabChange('consult')}
                />
                <TabButton
                    label="최종 보고서 조합"
                    isActive={activeTab === 'report'}
                    onClick={() => onTabChange('report')}
                />
            </nav>
        </div>
    );
};

