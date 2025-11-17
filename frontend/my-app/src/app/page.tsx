"use client";

import React, { useState } from "react";

interface Message {
  id: number;
  role: "user" | "assistant";
  content: string;
}

export default function Home() {
  const [messages, setMessages] = useState<Message[]>([
    { id: 1, role: "assistant", content: "안녕하세요! 무엇을 도와드릴까요?" },
  ]);
  const [input, setInput] = useState("");

  const handleSend = async () => {
    if (!input.trim()) return;

    // textarea 안의 글자를 alert로 띄우기
    alert(input);

    const newMessage: Message = { id: messages.length + 1, role: "user", content: input };
    const updatedMessages: Message[] = [...messages, newMessage];
    setMessages(updatedMessages);
    setInput("");
    console.log("입력한 검색어 :  ", updatedMessages);

    // 백엔드 API로 검색어 전송 (API Gateway 패턴 사용)
    // Eureka Discovery를 거쳐 soccerservice → SoccerSearchController → SoccerSearchFacade → PlayerService로 데이터 이동
    try {
      const { soccerService } = await import('@/services/soccer.service');
      const data = await soccerService.searchByKeyword({
        keyword: input,
        type: "player",
      });
      console.log("백엔드 응답:", data);

      // 검색 결과를 메시지로 표시
      if (data && data.length > 0) {
        const searchResult: Message = {
          id: updatedMessages.length + 1,
          role: "assistant",
          content: `검색 결과를 찾았습니다: ${data.length}개의 결과가 있습니다.`,
        };
        setMessages((prev: Message[]) => [...prev, searchResult]);
      }
    } catch (error) {
      console.error("API 요청 중 오류 발생:", error);
      const errorMessage: Message = {
        id: updatedMessages.length + 1,
        role: "assistant",
        content: "검색 중 오류가 발생했습니다. 다시 시도해주세요.",
      };
      setMessages((prev: Message[]) => [...prev, errorMessage]);
    }

    // AI 응답 시뮬레이션
    setTimeout(() => {
      const aiResponse: Message = {
        id: updatedMessages.length + 1,
        role: "assistant",
        content: "메시지를 받았습니다. 실제 AI 연동은 백엔드 API를 통해 구현할 수 있습니다.",
      };
      setMessages((prev: Message[]) => [...prev, aiResponse]);
    }, 500);
  };

  const handleKeyPress = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault();
      handleSend();
    }
  };

  return (
    <div className="flex h-screen bg-gray-900 text-white">
      {/* 사이드바 */}
      <div className="w-64 bg-gray-800 border-r border-gray-700 flex flex-col">
        <div className="p-4 border-b border-gray-700">
          <button className="w-full px-4 py-2 bg-gray-700 hover:bg-gray-600 rounded-lg text-sm font-medium transition-colors">
            + 새 채팅
          </button>
        </div>
        <div className="flex-1 overflow-y-auto p-2">
          <div className="text-xs text-gray-400 px-2 py-1 mb-2">오늘</div>
          <div className="px-2 py-2 hover:bg-gray-700 rounded-lg cursor-pointer text-sm">
            새 대화
          </div>
        </div>
        <div className="p-4 border-t border-gray-700">
          <div className="flex items-center gap-3">
            <div className="w-8 h-8 bg-gray-600 rounded-full flex items-center justify-center text-sm font-semibold">
              U
            </div>
            <div className="flex-1 text-sm">
              <div className="font-medium">사용자</div>
            </div>
          </div>
        </div>
      </div>

      {/* 메인 채팅 영역 */}
      <div className="flex-1 flex flex-col">
        {/* 헤더 */}
        <div className="h-14 bg-gray-800 border-b border-gray-700 flex items-center px-4">
          <h1 className="text-lg font-semibold">ChatGPT</h1>
        </div>

        {/* 메시지 영역 */}
        <div className="flex-1 overflow-y-auto p-4 space-y-4">
          {messages.map((message: Message) => (
            <div
              key={message.id}
              className={`flex gap-4 ${message.role === "user" ? "justify-end" : "justify-start"
                }`}
            >
              {message.role === "assistant" && (
                <div className="w-8 h-8 bg-green-600 rounded-full flex items-center justify-center flex-shrink-0">
                  <svg
                    className="w-5 h-5"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"
                    />
                  </svg>
                </div>
              )}
              <div
                className={`max-w-2xl rounded-lg px-4 py-3 ${message.role === "user"
                  ? "bg-gray-700 text-white"
                  : "bg-gray-800 text-gray-100"
                  }`}
              >
                <div className="whitespace-pre-wrap">{message.content}</div>
              </div>
              {message.role === "user" && (
                <div className="w-8 h-8 bg-gray-600 rounded-full flex items-center justify-center flex-shrink-0">
                  <span className="text-sm font-semibold">U</span>
                </div>
              )}
            </div>
          ))}
        </div>

        {/* 입력 영역 */}
        <div className="border-t border-gray-700 p-4 bg-gray-800">
          <div className="max-w-3xl mx-auto">
            <div className="relative flex items-end gap-2 bg-gray-700 rounded-lg border border-gray-600 focus-within:border-gray-500">
              <textarea
                value={input}
                onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) => setInput(e.target.value)}
                onKeyPress={handleKeyPress}
                placeholder="메시지를 입력하세요..."
                className="flex-1 bg-transparent px-4 py-3 text-white placeholder-gray-400 resize-none focus:outline-none max-h-32"
                rows={1}
              />
              <button
                onClick={handleSend}
                disabled={!input.trim()}
                className="m-2 p-2 rounded-lg bg-gray-600 hover:bg-gray-500 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
              >
                <svg
                  className="w-5 h-5"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8"
                  />
                </svg>
              </button>
            </div>
            <div className="text-xs text-gray-400 mt-2 text-center">
              ChatGPT는 실수를 할 수 있습니다. 중요한 정보를 확인하세요.
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
