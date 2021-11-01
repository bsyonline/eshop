local uri_args = ngx.req.get_uri_args()
local productId = uri_args["productId"]

local hosts = {"192.168.67.201", "192.168.67.202"}

local hash = ngx.crc32_long(productId)
local index = (hash % 2) + 1
backend = "http://"..hosts[index]

local requestPath = uri_args["requestPath"]
requestPath = "/"..requestPath.."?productId="..productId

local http = require "resty.http"
local httpc = http.new()

local resp, err = httpc:request_uri(backend,{
    method = "GET",
    path = requestPath
})

if not resp then
    ngx.say("request error: ", err)
    return
end

ngx.say(resp.body)

httpc:close()
