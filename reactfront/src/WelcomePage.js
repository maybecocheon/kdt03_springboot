import { useNavigate } from "react-router-dom"
import "./css/welcome.css"

export default function WelcomePage() {
    const navigate = useNavigate()
    const logout = () => {
        if (window.confirm("정말 로그아웃하시겠습니까?")) {
            sessionStorage.removeItem("jwtToken")
            sessionStorage.removeItem("username")
            alert("로그아웃되었습니다.")
            navigate("/login")
        }
    }

    return (
        <div className="welcome-container">
            <div className="header">
                <h1 id="welcomeMessage">환영합니다!</h1>
                <button className="logout-btn" onClick={logout}>로그아웃</button>
            </div>
        </div>
    )
}
