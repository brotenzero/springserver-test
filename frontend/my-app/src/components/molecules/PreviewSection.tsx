/**
 * Molecules: PreviewSection
 * 프리뷰 섹션 컴포넌트
 */
import React from 'react';

interface HighlightSourceProps {
    text: string;
    tooltip?: string;
    variant?: 'default' | 'error';
}

const HighlightSource: React.FC<HighlightSourceProps> = ({
    text,
    tooltip,
    variant = 'default',
}) => {
    const baseStyles = 'cursor-pointer position-relative';
    const variantStyles = {
        default: 'bg-yellow-100 border-b-2 border-amber-500',
        error: 'bg-red-100 border-b-2 border-red-400',
    };

    return (
        <span className={`highlight-source ${baseStyles} ${variantStyles[variant]}`}>
            {text}
            {tooltip && (
                <span className="tooltip">{tooltip}</span>
            )}
        </span>
    );
};

interface AICommentProps {
    type: 'info' | 'error';
    content: string;
}

const AIComment: React.FC<AICommentProps> = ({ type, content }) => {
    const styles = {
        info: 'bg-blue-50 border border-blue-200 text-blue-700',
        error: 'bg-red-50 border border-red-200 text-red-700',
    };

    return (
        <div className={`${styles[type]} p-3 rounded-md text-sm mt-3`}>
            <strong>AI 코멘트:</strong> {content}
        </div>
    );
};

interface PreviewSectionProps {
    title: string;
    content: React.ReactNode;
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

export const PreviewSection: React.FC<PreviewSectionProps> = ({
    title,
    content,
    highlights = [],
    aiComment,
}) => {
    const renderContent = () => {
        if (typeof content === 'string') {
            // 하이라이트가 있는 경우 텍스트를 파싱하여 하이라이트 적용
            if (highlights.length > 0) {
                let parts: React.ReactNode[] = [];
                let lastIndex = 0;
                let highlightIndex = 0;

                // 각 하이라이트를 찾아서 적용
                highlights.forEach((highlight, idx) => {
                    const searchText = highlight.text;
                    const index = content.indexOf(searchText, lastIndex);

                    if (index !== -1) {
                        // 하이라이트 전 텍스트
                        if (index > lastIndex) {
                            parts.push(<span key={`text-${idx}`}>{content.substring(lastIndex, index)}</span>);
                        }
                        // 하이라이트
                        parts.push(
                            <HighlightSource
                                key={`highlight-${idx}`}
                                text={searchText}
                                tooltip={highlight.tooltip}
                                variant={highlight.variant}
                            />
                        );
                        lastIndex = index + searchText.length;
                    }
                });

                // 남은 텍스트
                if (lastIndex < content.length) {
                    parts.push(<span key="text-end">{content.substring(lastIndex)}</span>);
                }

                return <p>{parts.length > 0 ? parts : content}</p>;
            }
            return <p>{content}</p>;
        }
        return content;
    };

    return (
        <div className="bg-white p-6 rounded-lg shadow-sm border border-gray-200 mb-6">
            <h3 className="text-lg font-semibold text-gray-800 mb-3">{title}</h3>
            <div className="prose prose-sm max-w-none text-gray-700 space-y-3">
                {renderContent()}
                {aiComment && (
                    <AIComment type={aiComment.type} content={aiComment.content} />
                )}
            </div>
        </div>
    );
};

