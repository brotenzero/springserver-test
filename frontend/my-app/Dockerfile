# Stage 1: 의존성 설치 및 빌드
FROM node:20-alpine AS builder

# pnpm 설치
RUN corepack enable && corepack prepare pnpm@latest --activate

WORKDIR /app

# 의존성 파일 복사
COPY package.json pnpm-lock.yaml ./

# 의존성 설치
RUN pnpm install --frozen-lockfile

# 소스 코드 복사
COPY . .

# Next.js 빌드
RUN pnpm build

# Stage 2: 프로덕션 실행
FROM node:20-alpine AS runner

WORKDIR /app

ENV NODE_ENV production

# pnpm 설치
RUN corepack enable && corepack prepare pnpm@latest --activate

# 필요한 파일만 복사
COPY --from=builder /app/.next/standalone ./
COPY --from=builder /app/.next/static ./.next/static

# 포트 노출
EXPOSE 3000

ENV PORT 3000
ENV HOSTNAME "0.0.0.0"

# 앱 실행
CMD ["node", "server.js"]

