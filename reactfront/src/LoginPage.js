import { useState } from "react"
import { useNavigate } from "react-router-dom"
import "./css/login.css"

export default function LoginPage() {
    const navigate = useNavigate();
    const [credentials, setCredentials] = useState({ username: "", password: ""})

    const handleChange = (e) => {
        setCredentials({ ...credentials, [e.target.id]: e.target.value})
    }

    const loginButton = async () => {
        try {
            const resp = await fetch("http://localhost:8080/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(credentials)
            })

            if (resp.ok) {
                const jwtToken = resp.headers.get("Authorization")
                if (jwtToken) {
                    sessionStorage.setItem("jwtToken", jwtToken)
                    sessionStorage.setItem("username", credentials.username)
                }
                navigate("/welcome")
            } else {
                alert("로그인 실패!")
            }
        } catch (error) {
            console.error("로그인 오류!", error)
        }
    }
    return (
        <div className="login-container" style={{ textAlign: "center"}}>
            <h2>로그인</h2>
            <div className="form-group">
                <label htmlFor="username">사용자명</label>
                <input type="text" id="username" placeholder="사용자명을 입력하세요"
                        value={credentials.username} onChange={handleChange}/>
            </div>

            <div className="form-group">
                <label htmlFor="password">비밀번호</label>
                <input type="password" id="password" placeholder="비밀번호를 입력하세요"
                        value={credentials.password} onChange={handleChange}/>
            </div>

            <button type="button" className="login-btn" onClick={loginButton}>로그인</button>

            <div className="divider">
                <span>또는</span>
            </div>

            <a href="http://localhost:8080/oauth2/authorization/google"
                className="google-login">구글 로그인</a>
        </div>
    )
}
