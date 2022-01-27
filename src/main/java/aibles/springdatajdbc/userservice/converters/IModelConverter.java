package aibles.springdatajdbc.userservice.converters;

/**
 * This interface response to convert from Entity to DTO and DTO to Entity
 * @param <Entity> is Entity model
 * @param <RequestDTO> is RequestDTO model that is passed through body in api
 * @param <ResponseDTO> is ResponseDTO model that is returned to client
 */
public interface IModelConverter<Entity, RequestDTO, ResponseDTO> {

    ResponseDTO convertToDTO(Entity entity);
    Entity convertToEntity(RequestDTO dto);

}
