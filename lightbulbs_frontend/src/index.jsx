import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { Frame } from "./frame";

createRoot(document.getElementById("app")).render(
  <StrictMode>
    <Frame />
  </StrictMode>,
);
