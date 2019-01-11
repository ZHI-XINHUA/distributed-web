package zxh.mongodb.spring_data_mongodb.converter;

import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 类型转换器：时间类型转换，Date转换为Timestamp
 * SpringData默认的时间类型是java.util.Date，而实体类中的时间类型是Timestamp，所以需要转换一下
 */
public class TimestampConverter implements Converter<Date,Timestamp> {
    @Override
    public Timestamp convert(Date date) {
        if(date==null){
            return null;
        }

        return new Timestamp(date.getTime());
    }
}
