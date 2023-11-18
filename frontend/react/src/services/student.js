import axios from "axios";

export const getStudents = async ()=>{
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/student/get-students`)
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
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/api/v1/student/delete-student/${studentId}`)
    }catch (e) {
        throw e;
    }
}

export const updateStudent = async (studentId,update)=>{
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/student/update-student/${studentId}`,
            update
        )
    }catch (e) {
        throw e;
    }
}

export const getStudentById = async (studentId)=>{
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/student/get-student/${studentId}`)
    }catch (e) {
        throw e;
    }
}
