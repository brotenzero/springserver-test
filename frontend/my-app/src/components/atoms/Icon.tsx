/**
 * Atoms: Icon
 * Lucide 아이콘 래퍼 컴포넌트
 */
import React from 'react';
import * as LucideIcons from 'lucide-react';

interface IconProps {
  name: keyof typeof LucideIcons;
  size?: number;
  className?: string;
  color?: string;
}

export const Icon: React.FC<IconProps> = ({
  name,
  size = 20,
  className = '',
  color,
}) => {
  const IconComponent = LucideIcons[name] as React.ComponentType<{
    size?: number;
    className?: string;
    color?: string;
  }>;
  
  if (!IconComponent) {
    return null;
  }
  
  return (
    <IconComponent
      size={size}
      className={className}
      color={color}
    />
  );
};

