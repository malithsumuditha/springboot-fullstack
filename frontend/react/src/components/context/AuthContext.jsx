import {createContext, useContext, useEffect, useState} from "react";
import {login as performLogin} from "../../services/student.js";
import {jwtDecode} from "jwt-decode";

const AuthContext = createContext({});
const AuthProvider = ({ children }) => {
    const [student, setStudent] = useState(null);

    useEffect(()=>{
        let token = localStorage.getItem("access_token");
        if (token) {
            token = jwtDecode(token);
            setStudent({
                username: token.sub,
            })
        }
    },[])

    const login = async (usernameAndPassword) => {
        return new Promise((resolve,reject) => {
            performLogin(usernameAndPassword).then(res => {
                const jwtToken = res.headers["authorization"];
                localStorage.setItem("access_token",jwtToken);

                const decodedToken = jwtDecode(jwtToken);

                setStudent({
                    username: decodedToken.sub,
                })
                resolve(res);
            }).catch(err => {
                reject(err);
            })
        })
    }

    const logout = () => {
        localStorage.removeItem("access_token")
        setStudent(null)
    }

    const isStudentAuthenticated = () => {
        const token = localStorage.getItem("access_token");
        if (!token){
            return false;
        }

        const decodedToken = jwtDecode(token);
        if (Date.now()> decodedToken.exp * 1000){
            logout()
            return false;
        }

        return true;

    }

    return (
        <AuthContext.Provider value={{
            student,
            login,
            logout,
            isStudentAuthenticated
        }}>

            {children}

        </AuthContext.Provider>
    )
}

export const useAuth = () => useContext(AuthContext);

export default AuthProvider;