'use client'

import {
    Button,
    Checkbox,
    Flex,
    Text,
    FormControl,
    FormLabel,
    Heading,
    Input,
    Stack,
    Image, Link, Box, Alert, AlertIcon,
} from '@chakra-ui/react'
import {Formik, useField, Form} from "formik";
import * as Yup from "yup";
import {useAuth} from "../context/AuthContext.jsx";
import {errorNotification} from "../../services/notification.js";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

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

const LoginForm = () => {

    const { login } = useAuth();
    const navigate = useNavigate();

    return (
        <Formik
            validateOnMount={true}
            validationSchema={
                Yup.object({
                    username: Yup.string()
                        .email("Must be valid email")
                        .required("Email is required"),
                    password: Yup.string()
                        .max(20, "Password cannot be more than 20 characters")
                        .required("Password is required")
                })
            }
            initialValues={{username: '', password: ''}}
            onSubmit={(values, {setSubmitting}) => {
                setSubmitting(true);
                login(values).then(res => {
                    navigate("/dashboard");
                }).catch(err => {
                    errorNotification(
                        err.code,
                        err.response.data.message
                    );
                }).finally(() => {
                    setSubmitting(false);
                })
            }}>

            {(formik) => (
                 <Form>
                    <Stack spacing={"24px"}>

                        <MyTextInput
                            label="Username"
                            name="username"
                            type="email"
                            placeholder="jane@email.com"
                        />

                        <MyTextInput
                            label="Password"
                            name="password"
                            type="password"
                            placeholder="Your Password"
                        />

                        <Button type="submit" bg={"teal"} color={"white"} isDisabled={!formik.isValid || formik.isSubmitting}>
                            Login
                        </Button>
                    </Stack>
                </Form>
            )}

        </Formik>
    )
}

const Login = () => {

    const {student} = useAuth();
    const navigate = useNavigate();

    useEffect(()=> {
        if (student){
            navigate("/dashboard");
        }
    })

    return (
        <Stack minH={'100vh'} direction={{base: 'column', md: 'row'}}>
            <Flex p={8} flex={1} alignItems={'center'} justifyContent={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'} >
                    <Stack alignItems={'center'}>
                        <Image
                            src={"https://media.licdn.com/dms/image/C560BAQFEvNlz15KUFQ/company-logo_200_200/0/1643476425386?e=2147483647&v=beta&t=J0tSHbYCmDR_hA7tih3xOm2JfObmj2-WXjgJIJ-Pbs0"}
                            boxSize={"200px"}
                            alt={"My Logo"}
                        />
                    </Stack>

                    <Heading fontSize={'2xl'}>Sign in to your account</Heading>
                    <LoginForm/>
                    <Link color={"blue.500"} href={"/signup"}>
                        Don't have an account? Signup now.
                    </Link>
                </Stack>
            </Flex>
            <Flex flex={1}
                  p={10}
                  flexDirection={"column"}
                  alignItems={"center"}
                  justifyContent={"center"}
                  bgGradient={{sm: 'linear(to-r, blue.600, purple.600)'}}
            >
                <Text fontSize={"6xl"} color={'white'} fontWeight={"bold"} mb={5}>
                    <Link target={"_blank"} href={"https://hashloopit.com"}>
                        Enrol Now
                    </Link>
                </Text>
                <Image
                    alt={'Login Image'}
                    objectFit={'scale-down'}
                    src={
                        'https://user-images.githubusercontent.com/40702606/215539167-d7006790-b880-4929-83fb-c43fa74f429e.png'
                    }
                />
            </Flex>
        </Stack>
    )
}

export default Login;