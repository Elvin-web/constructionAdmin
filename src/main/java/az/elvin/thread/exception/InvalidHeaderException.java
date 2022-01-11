package az.cc103.doctorNurseDriver.exception;

import az.cc103.doctorNurseDriver.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidHeaderException extends RuntimeException {

    private final ResponseEnum responseEnum;
}
