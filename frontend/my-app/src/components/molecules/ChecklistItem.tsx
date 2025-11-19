/**
 * Molecules: ChecklistItem
 * 체크리스트 항목 컴포넌트
 */
import React from 'react';
import { Icon } from '../atoms';

type Status = 'completed' | 'warning' | 'error';

interface ChecklistItemProps {
  label: string;
  status: Status;
}

export const ChecklistItem: React.FC<ChecklistItemProps> = ({
  label,
  status,
}) => {
  const statusConfig = {
    completed: {
      icon: 'CheckCircle' as const,
      color: 'text-green-500',
      textColor: 'text-gray-600',
    },
    warning: {
      icon: 'AlertCircle' as const,
      color: 'text-yellow-500',
      textColor: 'text-gray-900 font-medium',
    },
    error: {
      icon: 'XCircle' as const,
      color: 'text-red-500',
      textColor: 'text-gray-500',
    },
  };
  
  const config = statusConfig[status];
  
  return (
    <li className="flex items-center text-sm">
      <Icon name={config.icon} className={`${config.color} mr-2`} size={20} />
      <span className={config.textColor}>{label}</span>
    </li>
  );
};

