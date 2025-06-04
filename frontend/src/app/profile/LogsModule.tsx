"use client";

import { useEffect, useState } from "react";
import { backendUrl } from "@/app/config";
import Cookies from "js-cookie";
import { Card, CardContent } from "@/components/ui/card";

// Define the type for the log item
interface LogItem {
  userId: string;
  description: string;
  date: string;
  color: string;
}

export default function LogsModule() {
  const [logs, setLogs] = useState<LogItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchLogs = async () => {
      try {
        const token = Cookies.get("token");
        if (!token) {
          setError("Not logged in. Please log in first.");
          setLoading(false);
          return;
        }

        const response = await fetch(`${backendUrl}/api/v1/account/me/myJournal`, {
          method: "GET",
          headers: { 
            Authorization: `Bearer ${token}` 
          }, // 移除Content-Type
          credentials: "include" // 新增凭证配置
        });
        
        if (!response.ok) {
          const msg = await response.text();
          throw new Error(msg);
        }

        const data = await response.json();
        setLogs(data);
      } catch (err: any) {
        setError(err.message || "Failed to fetch logs.");
      } finally {
        setLoading(false);
      }
    };

    fetchLogs();
  }, []);

  const getLogTypeStyle = (color: string) => {
    switch (color.toLowerCase()) {
      case "blue":
        return "border-l-4 border-blue-500";
      case "green":
        return "border-l-4 border-green-500";
      case "yellow":
        return "border-l-4 border-yellow-500";
      case "red":
        return "border-l-4 border-red-500";
      default:
        return "";
    }
  };

  if (loading) return <p>Loading logs...</p>;
  if (error) return <p className="text-red-500">{error}</p>;
  if (!logs.length) return <p>No logs available.</p>;

  return (
    <div className="space-y-4">
      {logs.map((log, index) => (
        <Card key={index} className={getLogTypeStyle(log.color)}>
          <CardContent className="p-4">
            <div className="flex flex-col gap-1">
              <p className="font-medium">{log.description}</p>
              <p className="text-sm text-gray-500">{log.date}</p>
            </div>
          </CardContent>
        </Card>
      ))}
    </div>
  );
}
