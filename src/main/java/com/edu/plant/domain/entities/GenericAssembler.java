package com.edu.plant.domain.entities;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenericAssembler {

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    @Autowired
    private ModelMapper modelMapper;

    public Object mapTo(Object obj, Class<?> destinationClass) {

        // TODO Auto-generated method stub
        try {
            return modelMapper.map(obj, Class.forName(destinationClass.getName()));
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public List<?> mapTo(List<?> objects, Class<?> destinationClass) {

        // TODO Auto-generated method stub
        return objects.stream()
                .map(object -> mapTo(object, destinationClass))
                .collect(Collectors.toList());
    }

    public void transformTo(Object obj, Class<?> destinationClass) {
        // TODO Auto-generated method stub
        try {
            modelMapper.map(obj, Class.forName(destinationClass.getName()));
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


//
//    Converter<Date, String> newDateConverter = new Converter<Date, String>() {
//        public String convert(MappingContext<Date, String> context) {
//            //return context.getSource() == null ? null : context.getSource().toUpperCase();
//            return context.getSource() == null ? null : "TESTE";
//        }
//    };

}
