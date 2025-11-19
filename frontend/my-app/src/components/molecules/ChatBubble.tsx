/**
 * Molecules: ChatBubble
 * 채팅 말풍선 컴포넌트
 */
import React from 'react';
import { Icon } from '../atoms';

interface ChatBubbleProps {
    role: 'user' | 'assistant';
    content: string;
    sender?: string;
    icon?: keyof typeof import('lucide-react');
}

export const ChatBubble: React.FC<ChatBubbleProps> = ({
    role,
    content,
    sender,
    icon,
}) => {
    const isUser = role === 'user';

    return (
        <div className={`flex items-start space-x-3 ${isUser ? 'justify-end' : 'justify-start'}`}>
            {!isUser && (
        <div className="flex-shrink-0 bg-blue-600 p-2 rounded-full">
          {icon ? (
            <Icon name={icon} size={20} className="text-white" />
          ) : (
            <Icon name="MessageSquare" size={20} className="text-white" />
          )}
        </div>
            )}

            <div
                className={`p-4 rounded-lg max-w-lg ${isUser
                        ? 'bg-blue-100 rounded-br-none'
                        : 'bg-white border border-gray-200 rounded-tl-none'
                    }`}
            >
                {sender && (
                    <p className={`font-semibold mb-1 ${isUser ? 'text-gray-800' : 'text-blue-800'}`}>
                        {sender}
                    </p>
                )}
                <p className="text-gray-700 whitespace-pre-wrap">{content}</p>
            </div>

            {isUser && (
                <div className="flex-shrink-0 bg-gray-600 p-2 rounded-full">
                    <span className="text-sm font-semibold text-white">U</span>
                </div>
            )}
        </div>
    );
};

