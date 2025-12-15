import { useEffect } from "react"
import { useNavigate } from "react-router-dom"

export default function OAuth2Callback() {
    const navigate = useNavigate()
    useEffect(() => {
        const fetchCallback = async () => {
            try {
                const resp = await fetch("http://localhost:8080/api/jwtcallback", {
                    method: "POST",
                    credentials: "include"  // 쿠키 포함
                })
                if (resp.ok){
                    const jwtToken = resp.headers.get("Authorization")
                    console.log("jwtToken", jwtToken)
                    if (jwtToken) sessionStorage.setItem("jwtToken", jwtToken)
                    console.log("로그인 성공!")
                    navigate("/welcome")
                } else {
                    alert("JWT 검증 실패")
                    navigate("/login")
                }
            } catch (error) {
                console.error("서버 요청 오류", error)
                alert("서버 요청 오류")
                navigate("/login")
            }
        }
        fetchCallback()
    }, [navigate])


    return (
        <p>
            로그인 처리 중...
        </p>
    )
}
