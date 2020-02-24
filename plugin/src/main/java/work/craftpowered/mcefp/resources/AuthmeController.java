package work.craftpowered.mcefp.resources;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mcrest.properties.NetworkResponse;
import org.mcrest.utils.Token;
import org.mcrest.vo.ResponseVO;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import fr.xephi.authme.api.v3.AuthMeApi;
import work.craftpowered.mcefp.Mcefp;
import work.craftpowered.mcefp.network.NetworkInterface;

@Path("/authme/")
@Produces("application/json")
public class AuthmeController extends ServerResource implements NetworkInterface, NetworkResponse {

    public AuthMeApi authMeApi = AuthMeApi.getInstance();

    @GET
    @Path("test")
    public ResponseVO test () {
        return ResponseVO.success("test成功");
    }

    @POST
    @Path("register")
    public ResponseVO register (Representation entity) {
        try {
            Form form = new Form(entity);
            String username = form.getFirstValue("username");
            String password = form.getFirstValue("password");
            if (StringUtils.isBlank(username)) throw new IllegalArgumentException("游戏名字不能为空！");
            if (StringUtils.isBlank(password)) throw new IllegalArgumentException("密码不能为空！");
            if (!authMeApi.isRegistered(username))  {
                Player player = Bukkit.getPlayerExact(username);
                authMeApi.forceRegister(player, password);
                return ResponseVO.success("注册成功", Token.createToken(player.getUniqueId()));
            }
            return ResponseVO.failure(NetworkResponse.USER_REGISTERED, "用户已经存在!");
        } catch (IllegalArgumentException e) {
            return ResponseVO.failure(NetworkResponse.BAD_DATA_TYPE, e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseVO.failure(NetworkResponse.SERVER_ERROR, e.getMessage());
        }
    }

    @POST
    @Path("login")
    public ResponseVO login (Representation entity) {
        try {
            Form form = new Form(entity);
            String username = form.getFirstValue("username");
            String password = form.getFirstValue("password");
            if (StringUtils.isBlank(username)) throw new IllegalArgumentException("游戏名字不能为空！");
            if (StringUtils.isBlank(password)) throw new IllegalArgumentException("密码不能为空！");
            Player player = Bukkit.getPlayerExact(username);
            if (player != null) {
                if(authMeApi.checkPassword(username, password)) {
                    authMeApi.forceLogin(player);
                    Mcefp.getInstance().sendMessage(player, "新消息","登录成功");
                    return ResponseVO.success("登录成功", Token.createToken(player.getUniqueId()));
                }
                return ResponseVO.failure(LOGIN_WRONG, "帐号或密码错误");
            }
            return ResponseVO.failure(NO_PLAYER, "玩家不存在");
        } catch (IllegalArgumentException e) {
            return ResponseVO.failure(BAD_DATA_TYPE, e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseVO.failure(SERVER_ERROR, e.getMessage());
        }
    }


}
