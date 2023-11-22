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

const AddIcon = () => "+";
const CloseIcon = () => "x";
const CreateStudentDrawer = ({fetchStudents}) => {

    const {isOpen, onOpen, onClose} = useDisclosure()

    return <>
        <Button
            leftIcon={<AddIcon/>}
            colorScheme={"teal"}
            onClick={onOpen}
        >
            Create Student
        </Button>

        <Drawer isOpen={isOpen} onClose={onClose} size={"lg"}>
            <DrawerOverlay/>
            <DrawerContent>
                <DrawerCloseButton/>
                <DrawerHeader>Create new student</DrawerHeader>

                <DrawerBody>
                    <CreateStudentForm
                        onSuccess={fetchStudents}
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

export default CreateStudentDrawer;

