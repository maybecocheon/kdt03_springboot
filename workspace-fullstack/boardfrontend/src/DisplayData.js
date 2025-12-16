import { useState } from "react"

export default function DisplayData() {
    const [data, setData] = useState([])
    const [searchText, setSearchText] = useState('')
    const [selectedOption, setSelectedOption] = useState('id')
    const options = ['id', 'alias']

    const handleTextChange = (e) => {
        setSearchText(e.target.value)
    }

    const selectChange = (e) => {
        setSelectedOption(e.target.value)
    }

    const loadData = () => {
        let tableContent;
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
                        data.map(board => 
                            <tr key={board.id}>
                                <td>{board.id}</td>
                                <td>{board.title}</td>
                                <td>{board.member.alias}</td>
                                <td>{board.content}</td>
                                <td>{board.createDate}</td>
                            </tr>
                        )
                    }
                </tbody>
            </table>
        )
        return tableContent
    }

    const formSubmitHandler = async (e) => {
        e.preventDefault()  // form을 이용해서 submit을 하면 일반적으로 페이지를 다시 로딩하는데
                            // 이 메서드를 실행하면 다시 로드되는 것을 막는다.
        try {
            let searchOption = "";
            if (searchText !== "") {
                searchOption = "?" + selectedOption + "=" + searchText
            }
            const resp = await fetch("http://localhost:8080/board" + searchOption)
            const {key, jsondata} = await resp.json()
            console.log("key", key)
            setData(jsondata)
        } catch (error) {
            console.error("Error fetching Data: ", error)
        }
    }

    return (
        <div>
            <h1>게시판 목록</h1>
            <form onSubmit={formSubmitHandler}>
                <label>
                    <select id="searchType" value={selectedOption} onChange={selectChange}>
                        {
                            options.map((option, index) => 
                                <option key={index} value={option}>
                                    {option}
                                </option>
                            )
                        }
                    </select>
                    <input type="text" value={searchText} onChange={handleTextChange}/>
                </label>
                <button type="submit">search</button>
            </form>
            <div>{loadData()}</div>
        </div>
    )
}
