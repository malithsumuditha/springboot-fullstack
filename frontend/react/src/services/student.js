import axios from "axios";

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    }
})

export const getStudents = async ()=>{
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/student/get-students`,
            getAuthConfig())
    }catch (e) {
        throw e;
    }
}

export const saveStudent = async (student)=>{
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/student/save-student`,
        student
        )
    }catch (e) {
        throw e;
    }
}

export const deleteStudent = async (studentId)=>{
    try {
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/api/v1/student/delete-student/${studentId}`,
            getAuthConfig())
    }catch (e) {
        throw e;
    }
}

export const updateStudent = async (studentId,update)=>{
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/student/update-student/${studentId}`,
            update,
            getAuthConfig()
        )
    }catch (e) {
        throw e;
    }
}

export const getStudentById = async (studentId)=>{
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/student/get-student/${studentId}`,
            getAuthConfig())
    }catch (e) {
        throw e;
    }
}

export const login = async (userNameAndPassword)=>{
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/login`,
            userNameAndPassword)
    }catch (e) {
        throw e;
    }
}