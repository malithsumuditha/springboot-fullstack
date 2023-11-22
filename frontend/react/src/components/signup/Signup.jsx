import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";
import {Flex, Heading, Image, Link, Stack, Text} from "@chakra-ui/react";
import CreateStudentForm from "../shared/CreateStudentForm.jsx";
import {useEffect} from "react";

const Signup = () => {

    const {student, setStudentFromToken} = useAuth();
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

                    <Heading fontSize={'2xl'}>Create your account</Heading>
                    <CreateStudentForm
                        onSuccess={(token) => {
                            localStorage.setItem("access_token",token);
                            setStudentFromToken();
                            navigate("/dashboard");
                        }}
                    />
                    <Link color={"blue.500"} href={"/"}>
                        You already have an account? Signing now.
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

export default Signup;