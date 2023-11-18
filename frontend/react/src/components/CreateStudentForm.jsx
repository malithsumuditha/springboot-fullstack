import {Formik, Form, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {saveStudent} from "../services/student.js";
import {errorNotification, successNotification} from "../services/notification.js";

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


const MySelect = ({label, ...props}) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert mt={2} status={"error"} className="error">
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

// And now we can use these
const CreateStudentForm = ({ fetchStudents }) => {
    return (
        <>
            <Formik
                initialValues={{
                    name: '',
                    address: '',
                    email: '',
                    age: 0,
                    gender: '',
                }}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(15, 'Must be 15 characters or less')
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
                    gender: Yup.string()
                        .oneOf(
                            ['male', 'female'],
                            'Invalid Gender'
                        )
                        .required('Required'),
                })}
                onSubmit={(student, {setSubmitting}) => {
                    setSubmitting(true);
                    saveStudent(student)
                        .then(res => {
                            console.log(res);
                            successNotification(
                                "Student saved",
                                `${student.name} was successfully saved`
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

                            <MySelect label="Gender" name="gender">
                                <option value="">Select gender</option>
                                <option value="male">Male</option>
                                <option value="female">Female</option>
                            </MySelect>
                            <Button type="submit" isDisabled={!formik.isValid || formik.isSubmitting}>Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default CreateStudentForm;