package work.craftpowered.mcefp.resources;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mcrest.properties.NetworkResponse;
import org.mcrest.utils.Token;
import org.mcrest.vo.ResponseVO;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/command/")
@Produces("application/json")
public class CommandContoller extends ServerResource{

    @SuppressWarnings("unused")
    @POST
    @Path("execute")
    @Get
    public ResponseVO execute (Representation entity) {
        try {
            String token = org.restlet.Request.getCurrent().getHeaders().getFirstValue("Authorization");

            Form form = new Form(entity);
            String sender = form.getFirstValue("sender");
            Player player = Bukkit.getPlayerExact(sender);
            if(!Token.getUser_Id(token).equals(player.getUniqueId().toString()))
                return ResponseVO.success();
            String commandLine = form.getFirstValue("command");
            if (player != null) {
                Bukkit.dispatchCommand((CommandSender)player, commandLine);
                return ResponseVO.success("操作已经执行");
            }
            return ResponseVO.failure(NetworkResponse.NO_PLAYER, "玩家不存在");
        } catch (IllegalArgumentException e) {
            return ResponseVO.failure(NetworkResponse.BAD_DATA_TYPE, e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseVO.failure(NetworkResponse.SERVER_ERROR, e.getMessage());
        }
    }

}
