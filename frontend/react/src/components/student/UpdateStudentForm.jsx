import {Formik, Form, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {saveStudent, updateStudent} from "../../services/student.js";
import {errorNotification, successNotification} from "../../services/notification.js";

const MyTextInput = ({label, ...props}) => {

    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert mt={2} status={"error"} className="error">
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};


const UpdateStudentForm = ({ fetchStudents,initialValues, studentId }) => {
    return (
        <>
            <Formik
                initialValues={initialValues}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(25, 'Must be 25 characters or less')
                        .required('Required'),
                    address: Yup.string()
                        .max(20, 'Must be 20 characters or less')
                        .required('Required'),
                    email: Yup.string()
                        .email('Invalid email address')
                        .required('Required'),
                    age: Yup.number()
                        .min(16, 'Must be at least 16 years of age')
                        .max(100, 'Must be at less than 100 years of age')
                        .required('Required'),
                })}
                onSubmit={(updatedStudent, {setSubmitting}) => {
                    setSubmitting(true);
                    updateStudent(studentId,updatedStudent)
                        .then(res => {
                            console.log(res);
                            successNotification(
                                "Student updated",
                                `${updatedStudent.name} was updated`
                            )
                            fetchStudents();
                        }).catch(err => {
                        errorNotification(
                          err.code,
                          err.response.data.message
                        );
                    }).finally(() => {
                        setSubmitting(false);
                    })
                }}
            >

                {(formik) => (
                    <Form>
                        <Stack spacing={"24px"}>
                            <MyTextInput
                                label="Name"
                                name="name"
                                type="text"
                                placeholder="Jane"
                            />

                            <MyTextInput
                                label="Address"
                                name="address"
                                type="text"
                                placeholder="Your Address"
                            />

                            <MyTextInput
                                label="Email"
                                name="email"
                                type="email"
                                placeholder="jane@email.com"
                            />

                            <MyTextInput
                                label="Age"
                                name="age"
                                type="number"
                                placeholder="20"
                            />

                            <Button type="submit" isDisabled={!(formik.isValid && formik.dirty ) || formik.isSubmitting}>Update</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default UpdateStudentForm;