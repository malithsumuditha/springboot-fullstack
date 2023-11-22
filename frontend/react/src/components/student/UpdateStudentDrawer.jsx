import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent, DrawerFooter,
    DrawerHeader,
    DrawerOverlay, Input, useDisclosure
} from "@chakra-ui/react";
import CreateStudentForm from "../shared/CreateStudentForm.jsx";
import UpdateStudentForm from "./UpdateStudentForm.jsx";
import React from "react";

const AddIcon = () => "+";
const CloseIcon = () => "x";
const UpdateIcon = () => "*";
const UpdateStudentDrawer = ({fetchStudents, initialValues,studentId}) => {

    const {isOpen, onOpen, onClose} = useDisclosure()

    return <>
        <Button
            bg={"yellow.400"}
            color={'white'}
            rounded={'full'}
            _hover={{
                transform: 'translateY(-2px)',
                boxShadow: 'lg'
            }}
            onClick={onOpen}
        >
            Update Student
        </Button>

        <Drawer isOpen={isOpen} onClose={onClose} size={"lg"}>
            <DrawerOverlay/>
            <DrawerContent>
                <DrawerCloseButton/>
                <DrawerHeader>Update {initialValues.name}</DrawerHeader>

                <DrawerBody>
                    <UpdateStudentForm
                        fetchStudents={fetchStudents}
                        initialValues={initialValues}
                        studentId={studentId}
                    />
                </DrawerBody>

                <DrawerFooter>
                    <Button
                        leftIcon={<CloseIcon/>}
                        colorScheme={"teal"}
                        onClick={onClose}
                    >
                        Close
                    </Button>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
    </>
}

export default  UpdateStudentDrawer;

