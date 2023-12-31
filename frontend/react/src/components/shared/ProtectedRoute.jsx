import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import {useAuth} from "../context/AuthContext.jsx";

const ProtectedRoute = ({children}) => {

    const {isStudentAuthenticated} =  useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (!isStudentAuthenticated()){
            navigate("/");
        }
    })

    return isStudentAuthenticated() ? children : "";
}

export default ProtectedRoute;