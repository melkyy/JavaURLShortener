import type { user } from "./../type/user";

const endpointRegister = "http://localhost:8081/register";
const endpointLogin = "http://localhost:8081/login";
export const UserService = {
  async SignUp(username: string, password: string): Promise<any> {
    try {
      await fetch(endpointRegister, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: username,
          password: password,
        }),
      });
      return { success: true };
    } catch (error) {
      return { success: false, message: error };
    }
  },
  async Login(username: string, password: string): Promise<any> {
    try {
      const res: any = await fetch(endpointLogin, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: username,
          password: password,
        }),
      });
      const data = await res.json();
      if (data?.token) {
        localStorage.setItem("t", data?.token);
        return { success: true };
      } else {
        return { success: false, message: "Incorrect Username or Password" };
      }
    } catch (error) {
      debugger;
      return { success: false, message: "An error has occurred" };
    }
  },
};
