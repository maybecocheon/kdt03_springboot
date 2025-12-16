import { useEffect, useState } from "react"
import axios from 'axios'

export default function DisplayData4() {
    const [dataMember, setDataMember] = useState([])
    const [dataBoard, setDataBoard] = useState([])
    // 화면에 출력할 데이터 타입 설정(member/board)
    const [showItem, setShowItem] = useState([])

    useEffect(() => {
        async function fetchData(url) {
            await axios.get(url, {
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer TestString"
                }
            })
                .then(resp => {
                    if (showItem === "member") {
                        setDataMember(resp.data.jsondata)
                    } else {
                        setDataBoard(resp.data.jsondata)
                    }
                })
                .catch(error => {
                    console.error("Error fetching Data: " + error)
                })
        }
        fetchData("http://localhost:8080/" + showItem)
    }, [showItem])

    const loadData = () => {
        let tableContent;
        if (showItem === "member") {
            tableContent = (
                <table align="center">
                    <thead>
                        <tr>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Alias</th>
                            <th>Role</th>
                            <th>Enabled</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            dataMember.map(member => (
                                <tr key={member.username}>
                                    <td>{member.username}</td>
                                    <td>[encrypted]</td>
                                    <td>{member.alias}</td>
                                    <td>{member.role}</td>
                                    <td>{member.enalbed ? "active" : "inactive"}</td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            )
        } else if (showItem === "board") {
            tableContent = (
                <table align="center">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>제목</th>
                            <th>글쓴이</th>
                            <th>내용</th>
                            <th>작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            dataBoard.map(board => (
                                <tr key={board.id}>
                                    <td>{board.id}</td>
                                    <td>{board.title}</td>
                                    <td>{board.member.alias}</td>
                                    <td>{board.content}</td>
                                    <td>{board.createDate}</td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            )
        } else {
            console.log("Not Selected")
            tableContent = (
                <table align="center">
                    <tr>
                        <td>선택하세요.</td>
                    </tr>
                </table>
            )
        }
        return tableContent
    }

    return (
        <div>
            <h2>Data Display4</h2>
            <button onClick={() => setShowItem("member")}>Member</button>
            <button onClick={() => setShowItem("board")}>Board</button>
            <div>{loadData()}</div>
        </div>
    )
}
