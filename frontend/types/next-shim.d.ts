// 兼容开发环境/工具链无法正确识别部分模块（如 next/link、react）的问题。
// 有真实依赖时，这里只是补充最小声明，避免 linter 报错，不影响运行。

declare module "next/link";
declare module "next/navigation";
declare module "react";
declare module "react-dom";



