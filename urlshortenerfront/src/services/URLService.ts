import type { UrlShortener as url } from "../type/url";

const urlAPI = "http://localhost:8081/url";
const token = localStorage.getItem("t");

export const URLService = {
  async getAllUrls(): Promise<url[]> {
    const response = await fetch(urlAPI, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
  },
  async createUrl(
    originalUrl: string,
  ): Promise<{ success: boolean; data?: url; message?: string }> {
    try {
      const response = await fetch(urlAPI, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          originalUrl: originalUrl,
          createdDate: new Date().toLocaleDateString(),
          userId: 1,
        }),
      });

      return { success: true, data: await response.json() };
    } catch (error) {
      return { success: false, message: "An error has occurred" };
    }
  },
  async deleteUrl(id: number): Promise<{ success: boolean }> {
    try {
      await fetch(`${urlAPI}/${id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return { success: true };
    } catch (error) {
      return { success: false };
    }
  },
};
