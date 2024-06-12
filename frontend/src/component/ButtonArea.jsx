import React from 'react';
import axios from 'axios';

export default function ButtonArea () {
    const addUser　= (name) => {
        const body = {name}
        axios.post("http://localhost:8080/users",body)
           .then((res)=>{
                        console.log('ok')
                     })
                     .catch(err => {
                         console.log("err:", err)
                     });
        }

    return (
        <div>
            <button　onClick ={()=>addUser("おかだ")} >ユーザー追加</button>
            <button>ユーザー削除</button>
            </div>
        )
    }