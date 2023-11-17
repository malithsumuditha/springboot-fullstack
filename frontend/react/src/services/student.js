import axios from "axios";

export const getStudents = async ()=>{
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/student/get-students`)
    }catch (e) {
        throw e;
    }
}
